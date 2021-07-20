package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
@Component
public class main implements CommandLineRunner {
    private final IngredientService ingredientService;
    private final ShampooService shampooService;


    public main(IngredientService ingredientService, ShampooService shampooService) {
        this.ingredientService = ingredientService;
        this.shampooService = shampooService;

    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
//task 1
//        System.out.println("Task 1");
//        System.out.println("Type Shampoo size: ");
//        Size size = Size.valueOf(scanner.nextLine());
//        shampooService.getAllBySizeOrderById(size).forEach(System.out::println);
//
//        // task 2
//        System.out.println("Task 2");
//        System.out.println("Type Shampoo size: ");
//        size = Size.valueOf(scanner.nextLine());
//        System.out.println("Type Shampoo LabelId: ");
//        Long id = Long.parseLong(scanner.nextLine());
//        shampooService.findShampooBySizeOrLabel_IdOrderByPrice(size, id).forEach(System.out::println);

        // task 3

//        System.out.println("Task 3");
//        System.out.println("Enter price:");
//        BigDecimal price = new BigDecimal(scanner.nextLine());
//        shampooService.findAllByPriceGreaterThanOrderByPriceDesc(price).forEach(System.out::println);
        // task 4

//        System.out.println("task 4");
//        System.out.println("Enter start with letters:");
//        String s = scanner.nextLine();
//        ingredientService.findAllByNameStartingWith(s).forEach(System.out::println);

        // task 5
//        System.out.println("task 5");
//        System.out.println("Enter names:");
//        List<String> names = Arrays.stream(scanner.nextLine().split("\\s+")).toList();
//        this.ingredientService.findAllByNameIn(names).forEach(System.out::println);

        // 6
//        System.out.println("task 6");
//        System.out.println("Enter price:");
//        BigDecimal price = new BigDecimal(scanner.nextLine());
//        System.out.println(this.shampooService.countAllByPriceLessThan(price));

        // 7
//        System.out.println("Task 7");
//        System.out.println("Enter Ingredient names:");
//        List<String> names = Arrays.stream(scanner.nextLine().split("\\s+")).toList();
//        shampooService.findAllShampooByIngredientNames(names).forEach(System.out::println);

        //8
//        System.out.println("Task 8");
//        System.out.println("Enter Ingredient count:");
//        int number = Integer.parseInt(scanner.nextLine());
//        shampooService.findShampooByNumberOfIngredients(number).forEach(System.out::println);
    //9
//        System.out.println("Task 9");
//        System.out.println("Enter ingredient name:");
//        String name = scanner.nextLine();
//        this.ingredientService.deleteAllByName(name);

        //10
        System.out.println("Task 10");
        this.ingredientService.findAll().stream().map(Ingredient::getPrice).forEach(System.out::println);
        this.ingredientService.updateIngredientPriceBy10Percent();
        this.ingredientService.findAll().stream().map(Ingredient::getPrice).forEach(System.out::println);

    }
}
