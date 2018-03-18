package ru.compscicenter.spb_realty.ParserGorodGov;


import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        int fromIndex = 1;
        int toIndex = 206500;
        if (args.length > 1) {
            fromIndex = Integer.parseInt(args[1]);
        }
        if (args.length > 2) {
            toIndex = Integer.parseInt(args[2]);
        }

        MongoService mongoService = new MongoService();
        Controller.run(fromIndex, toIndex, mongoService);
    }


}
