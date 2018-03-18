package ru.compscicenter.spb_realty.ParserGorodGov;


import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        int fromIndex = 1;
        int toIndex = 206500;
        if (args.length > 0) {
            fromIndex = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            toIndex = Integer.parseInt(args[1]);
        }

        MongoService mongoService = new MongoService();
        Controller ctrl = new Controller(fromIndex, toIndex, mongoService);
        ctrl.run(32);
    }


}
