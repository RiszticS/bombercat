package org.ctrlaltdefeat.controllers;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ResourceWalker {
    public static ArrayList<String> walk(String folder) throws URISyntaxException, IOException {
        URI uri = ResourceWalker.class.getResource(folder).toURI();
        Path myPath;
        ArrayList<String> result = new ArrayList<>();
        if (uri.getScheme().equals("jar")) {
            try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap())) {
                myPath = fileSystem.getPath(folder);

                try (Stream<Path> walk = Files.walk(myPath, 1)) {
                    for (Iterator<Path> it = walk.iterator(); it.hasNext();){
                        result.add(it.next().toString());
                    }
                }
                result.removeFirst();
                return result;
            }
        } else {
            myPath = Paths.get(uri);

            try (Stream<Path> walk = Files.walk(myPath, 1)) {
                for (Iterator<Path> it = walk.iterator(); it.hasNext();){
                    result.add(it.next().toString());
                    System.out.println(result.getLast());
                }
            }

            result.removeFirst();
            return result;
        }
    }
}