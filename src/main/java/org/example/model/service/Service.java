package org.example.model.service;

import org.example.model.Producer;
import org.example.model.Souvenir;
import org.example.repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Service {
    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public List<Producer> findAllProducer() {
        return repository.findAllProducer();
    }

    public List<Souvenir> findAllSouvenir() {
        return repository.findAllSouvenir();
    }

    public void save(Producer producer) {
        List<Producer> data = repository.findAllProducer();
        data.add(producer);
        repository.saveProducers(data);
    }

    public void save(Souvenir souvenir) {
        List<Souvenir> data = repository.findAllSouvenir();
        data.add(souvenir);
        repository.saveSouvenirs(data);
    }

    public List<Souvenir> findSouvenirByProducer(Producer producer) {
        return repository.findAllSouvenir()
                .stream()
                .filter(s -> Objects.equals(s.getProducer().getId(), producer.getId()))
                .toList();
    }

    public List<Souvenir> findSouvenirByCountry(String country) {
        return repository.findAllSouvenir()
                .stream()
                .filter(s -> Objects.equals(s.getProducer().getCountry(), country))
                .toList();
    }

    public List<Producer> findProducerIfPriceLess(double price) {
        return repository.findAllSouvenir()
                .stream()
                .filter(s -> s.getPrice() < price)
                .map(Souvenir::getProducer)
                .distinct()
                .toList();
    }

    public Map<Producer, List<Souvenir>> findProducerWithAllTheySouvenirs() {
        return repository.findAllSouvenir()
                .stream()
                .collect(Collectors.groupingBy(Souvenir::getProducer));
    }

    public List<Producer> findProducerByYear(int year) {
        return repository.findAllSouvenir()
                .stream()
                .filter(s -> s.getYear() == year)
                .map(Souvenir::getProducer)
                .distinct()
                .toList();
    }

    public List<Souvenir> findSouvenirByYear(int year) {
        return repository.findAllSouvenir()
                .stream()
                .filter(s -> Objects.equals(s.getYear(), year))
                .toList();
    }

    public void deleteProducerWithSouvenirs(Producer producer) {
        List<Producer> producers = repository.findAllProducer()
                .stream()
                .filter(p -> !Objects.equals(p.getId(), producer.getId()))
                .toList();
        repository.saveProducers(producers);

        List<Souvenir> souvenirs = repository.findAllSouvenir()
                .stream()
                .filter(s -> !Objects.equals(s.getProducer().getId(), producer.getId()))
                .toList();
        repository.saveSouvenirs(souvenirs);
    }
}
