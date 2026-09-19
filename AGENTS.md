# AGENTS.md

Guidelines for AI agents and collaborators working on **PutLegalHeadersAuto**.

## Project at a glance

- **Windows-only** CLI tool built with **Java 8 + Maven**. The only dependency
  is `json-simple` (installed by Maven).
- Reads `config.json` (folder, suffix, search text, WITH/WITHOUT mode) and the
  header text from `inputText.txt`, then prepends that header to every matching
  file that lacks it.
- File discovery shells out to `cmd.exe` (`for /r` + `find`); header insertion
  rewrites each file through a `tempFile.txt` buffer.

## Architecture & conventions

| Directory | Responsibility |
| --- | --- |
| `src/main/java/main` | Entry point (`Main`) — keep tiny. |
| `src/main/java/controller` | `Controller` (startup orchestration) and `Functions` (file discovery + insertion). |
| `src/main/java/model` | `Model` — current config + header text. |
| `src/main/java/finals` | `Finals` — constants (file names, comment markers). |
| `src/main/java/logger` | Logging setup. |

Hard rules contributors must follow:

1. **Do not add third-party dependencies** unless strictly necessary.
2. **No literals in code.** File names, comment markers, and paths go in
   `finals.Finals`. Configuration-driven values stay in `config.json`.
3. **Keep the CLI style.** This repo is intentionally UI-free; UI belongs in the
   sibling project `put-legal-headers-with-ui`.
4. **Windows-aware.** The discovery command relies on `cmd.exe` syntax
   (`@for /r %f in (...) do @find /i ...`). Do not break that contract without a
   cross-platform fallback (see Roadmap in README).
5. **Preserve encoding.** All file I/O must use `UTF-8` as the existing code does.

## Code style

- Java 8 syntax.
- `java.util.logging` for diagnostics.
- `System.out.println` is used for user-facing CLI output (keep it, but prefer
  structured output when extending).
- Raw types are acceptable in the current codebase; prefer generics in new code.

## Known weak spots (candidates for improvement)

- Discovery is `cmd.exe`-dependent → not portable. Prefer `java.nio.file.Files.walk`.
- `config.json` ships with a hard-coded example path — should be validated/absent.
- Log file is named `%T/ReWarMe.log` (copied from another project) — rename it.
- `tempFile.txt` rewrite flow: consider in-memory rewrite to avoid transient states.
- No dry-run mode; files are modified as soon as the tool runs.

## Assets

- `assets/header.svg` and `assets/mockup.svg` are README showcase graphics.
  Keep them in sync with the README; do not remove without updating it.

## Assets

- `assets/header.svg` and `assets/mockup.svg` are README showcase graphics.
  Keep them in sync with the README; do not remove without updating it.

## How to run

```bash
mvn clean package
java -jar target/PutLeagalHeadersAuto-1.0-jar-with-dependencies.jar
```

> Integration-testing on non-Windows requires a Windows shell; do not run the
> tool on production/source-of-truth folders without a backup.

## Workflow

- Work on `main` with small, clearly-messaged commits.
- When asked to "optimize/update", prefer behavior-preserving refactors and
  confirm before touching the file-modification logic.
- Update `README.md` and `AGENTS.md` whenever structure or conventions change.