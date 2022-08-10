package edu.pdx.cs410J.agilston.phonebill;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import edu.pdx.cs410J.ParserException;

public class FileUtils {
    /**
     * Checks whether a file can be written to.
     *
     * @param filename file to write to
     * @return whether the file can be written to
     * @throws IOException       if the file is not valid
     * @throws SecurityException if the program cannot access the file due to OS security measures
     */
    private static boolean canWriteFile(String filename) throws IOException, SecurityException {
        File file = new File(filename);
        File parent;

        if((parent = file.getParentFile()) != null) {
            parent.mkdirs();
        }

        return file.createNewFile() || file.canWrite();
    }

    public static void save(Context context, PhoneBill bill) {
        String filename = formatFile(context, bill);

        try {
            if(!canWriteFile(filename)) {
                return;
            }

            TextDumper dumper = new TextDumper(new FileWriter(filename));
            dumper.dump(bill);
        }
        catch(IOException ignored) {
        }
    }

    public static void save(Context context, Collection<PhoneBill> bills) {
        for(PhoneBill bill : bills) {
            save(context, bill);
        }
    }

    public static Map<String, PhoneBill> loadAll(Context context) {
        Map<String, PhoneBill> map = new TreeMap<>();

        String internalStorage = getStorageFolder(context);
        File internalDir = new File(internalStorage);
        File[] files = internalDir.listFiles((dir, name) -> name.endsWith(".txt"));

        if(files != null) {
            for(File file : files) {
                try {
                    TextParser parser = new TextParser(new FileReader(file));
                    PhoneBill bill = parser.parse();
                    map.put(bill.getCustomer(), bill);
                }
                catch(FileNotFoundException | ParserException ignored) {
                }
            }
        }

        return map;
    }

    private static String formatFile(Context context, PhoneBill bill) {
        return formatFile(context, bill.getCustomer());
    }

    private static String formatFile(Context context, String customer) {
        return Paths.get(getStorageFolder(context), customer + ".txt").normalize().toString();
    }

    private static String getStorageFolder(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }
}
