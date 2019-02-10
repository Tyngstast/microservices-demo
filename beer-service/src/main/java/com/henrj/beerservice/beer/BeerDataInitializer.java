package com.henrj.beerservice.beer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeerDataInitializer implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerDataInitializer(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.findAll().isEmpty()) {
            log.info("** Repository empty -- initializing with new data");

            beerRepository.save(new Beer(1L, "Stigbergets", "Amazing Haze", 32));
            beerRepository.save(new Beer(2L, "Omnipollo", "Fatamorgana", 36));
            beerRepository.save(new Beer(3L, "Dugges", "High Five IPA", 28));
            beerRepository.save(new Beer(4L, "Falcon", "Export", 12));
            beerRepository.save(new Beer(5L, "Mariestads", "Export", 15));
        }
        beerRepository.findAll().forEach(beer -> log.info(beer.toString()));
    }
}
