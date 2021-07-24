package com.nitok.consulredisexample;

import com.nitok.consulredisexample.model.Product;
import com.nitok.consulredisexample.repository.RedisRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RedisExampleController {

    @Autowired
    public RedisRepository redisRepository;

    Logger logger = Logger.getLogger(RedisExampleController.class.getName());

    @GetMapping("/example")
    public void runExample() {
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

    @GetMapping("/products")
    public List<Product> getProducts() {
        return redisRepository.findAllProducts()
                .entrySet().stream()
                .map(objectObjectEntry -> new Product((String) objectObjectEntry.getKey(), (String)objectObjectEntry.getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/product")
    public Product getProductById(@RequestParam("id") String id) {
        return redisRepository.findProductById(id);
    }

    @DeleteMapping("/product")
    public void deleteProductById(@RequestParam("id") String id) {
        redisRepository.deleteById(id);
    }

    @PutMapping("/product")
    public void addProduct(@RequestParam("id") String id, @RequestParam("name") String name) {
        Product product = redisRepository.findProductById(id);
        if (product != null) {
            throw new RuntimeException("product with same id exists");
        }
        redisRepository.add(new Product(id, name));
    }

}
