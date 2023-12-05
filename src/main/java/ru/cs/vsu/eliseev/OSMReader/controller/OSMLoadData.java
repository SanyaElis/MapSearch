package ru.cs.vsu.eliseev.OSMReader.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class OSMLoadData {

    public static void main(String[] args) {
        double minLat = 51.6746;
        double maxLat = 51.6862;
        double minLon = 39.1745;
        double maxLon = 39.2082;

        try {
            HttpClient httpClient = HttpClients.createDefault();

            String osmApiUrl = "https://api.openstreetmap.org/api/0.6/map?bbox=" +
                    minLon + "," + minLat + "," + maxLon + "," + maxLat;

            HttpGet request = new HttpGet(osmApiUrl);
            HttpResponse response = httpClient.execute(request);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }

            writeToFile(result.toString(), "src/main/java/ru/cs/vsu/eliseev/OSMReader/osmdata/output.osm");

            System.out.println("Data written to file successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String data, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
        }
    }
}
