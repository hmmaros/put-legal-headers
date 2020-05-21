package main;

import controller.Controller;
import logger.MyLogger;

import java.util.logging.*;

public class Main {

    public static void main(String[] args) {
        Logger logger = MyLogger.myLogger();
        logger.log(Level.INFO, "Application started");

        new Controller();
    }



}
