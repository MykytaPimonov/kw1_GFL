package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Producer;
import org.example.model.Souvenir;
import org.example.model.dto.SouvenirDto;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Repository {

    public List<Producer> findAllProducer() {
        try (FileReader fr = new FileReader("data/producers.json");
             BufferedReader br = new BufferedReader(fr)) {

            String data = br.lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            Producer[] producers = mapper.readValue(data, Producer[].class);

            return Arrays.stream(producers).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Souvenir> findAllSouvenir() {
        try (FileReader fr = new FileReader("data/souvenirs.json");
             BufferedReader br = new BufferedReader(fr)) {

            String data = br.lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            SouvenirDto[] souvenirDtos = mapper.readValue(data, SouvenirDto[].class);
            Map<Integer, Producer> producers = findAllProducer().stream()
                    .collect(Collectors.toMap(Producer::getId, Function.identity()));

            return Arrays.stream(souvenirDtos)
                    .map(s -> new Souvenir(
                            s.getName(),
                            producers.getOrDefault(s.getProducer(), null),
                            s.getYear(),
                            s.getPrice()
                    ))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveProducers(List<Producer> producers) {
        try (FileWriter fw = new FileWriter("data/producers.json");
             BufferedWriter bw = new BufferedWriter(fw)) {

            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(producers);
            bw.write(data);
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSouvenirs(List<Souvenir> souvenirs) {
        try (FileWriter fw = new FileWriter("data/souvenirs.json");
             BufferedWriter bw = new BufferedWriter(fw)) {

            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(
                    souvenirs.stream()
                            .map(s -> new SouvenirDto(
                                    s.getName(),
                                    s.getProducer().getId(),
                                    s.getYear(),
                                    s.getPrice()
                            ))
                            .toList()
            );
            bw.write(data);
            bw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
