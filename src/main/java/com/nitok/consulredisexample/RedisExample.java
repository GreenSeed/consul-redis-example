package com.nitok.consulredisexample;

import com.nitok.consulredisexample.model.Product;
import com.nitok.consulredisexample.repository.RedisRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisExample {
    @Autowired
    public RedisRepository redisRepository;

    Logger logger = Logger.getLogger(RedisExample.class.getName());

    @PostConstruct
    public void init() {
        //Очищаем продукты перед началом работы
        redisRepository.deleteAllProducts();

        Product p1 = new Product("1", "phone");
        Product p2 = new Product("2", "notebook");
        logger.warn("Добавляем " + p1 + " и " + p2);
        redisRepository.add(p1);
        redisRepository.add(p2);

        logger.warn("Читаем продукты:");
        redisRepository.findAllProducts().forEach((o, o2) -> logger.warn(o2));

        logger.warn("Удаляем " + p1);
        redisRepository.deleteById(p1.getId());

        logger.warn("Снова читаем продукты:");
        redisRepository.findAllProducts().forEach((o, o2) -> logger.warn(o2));

        logger.warn("Удаляем все что есть");
        redisRepository.deleteAllProducts();

        logger.warn("Снова читаем продукты:");
        redisRepository.findAllProducts().forEach((o, o2) -> logger.warn(o2));
    }
}
