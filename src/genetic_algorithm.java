import Additionals.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class genetic_algorithm {
    public static void genetic(Graph graph, int V) {

        // Upper Bound for Coloring
        int max_num_colors = graph.getMax_am_colors();
        int number_of_colors = max_num_colors;
        boolean condition = true;
        int iter = 10000;
        while (condition && number_of_colors > 0) {
            int population_size = iter/5;
            int generation = 0;
            ArrayList<ArrayList<Integer>> population = new ArrayList<>();
            for (int i = 0; i < population_size; i++) {
                ArrayList<Integer> individual = create_individual(V, number_of_colors);
                population.add(individual);
            }
            int best_fitness = graph.count_fitness(population.get(0));
            ArrayList<Integer> fittest_individual = new ArrayList<>(population.get(0));
            int gen = 0;
            while (best_fitness != 0 && gen != iter) {
                gen += 1;
                population = new ArrayList<>(graph.roulette_wheel_selection(population));
                ArrayList<ArrayList<Integer>> new_population = new ArrayList<>();
                Collections.shuffle(population);
                for (int i = 0; i < population_size - 1; i += 2) {
                    ArrayList<ArrayList<Integer>> children = crossover(population.get(i), population.get(i + 1), V);
                    ArrayList<Integer> child1 = children.get(0);
                    ArrayList<Integer> child2 = children.get(1);
                    new_population.add(child1);
                    new_population.add(child2);
                }
                for (int i = 0; i < new_population.size(); i++) {
                    if (gen < iter / 5)
                        new_population.set(i, new ArrayList<>(mutation1(population.get(i), V, number_of_colors)));
                    else
                        new_population.set(i, new ArrayList<>(mutation2(population.get(i), V, number_of_colors)));
                }
                population = new ArrayList<>(new_population);
                best_fitness = graph.count_fitness(population.get(0));
                fittest_individual = new ArrayList<>(population.get(0));
                for (ArrayList<Integer> individual : population) {
                    if (graph.count_fitness(individual) < best_fitness) {
                        best_fitness = graph.count_fitness(individual);
                        fittest_individual = new ArrayList<>(individual);
                    }
                }
                if (gen % 10 == 0)
                    System.out.println("Generation: " + gen + " Best_Fitness: " + best_fitness + "Individual: " + fittest_individual.toString() + "\n Col: " + number_of_colors);
            }
            System.out.println("Using: " + number_of_colors + " colors \n\n");
            if (best_fitness != 0) {
                condition = false;
                number_of_colors += 1;
                System.out.println("Graph is " + number_of_colors + " colorable");
            } else
                number_of_colors -= 1;

        }
    }


    static ArrayList<Integer> create_individual(int V, int number_of_colors)
    {
        Random random = new Random();
        ArrayList<Integer> individual = new ArrayList<>();
        for(int i =0; i< V; i++)
        {
            individual.add(random.nextInt(number_of_colors));
        }

        return individual;
    }

    static ArrayList<ArrayList<Integer>> crossover(ArrayList<Integer> parent1, ArrayList<Integer>parent2, int V)
    {
        Random random = new Random();
        int position = random.nextInt(2, V-2);
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        for(int i=0;i<position+1;i++)
        {
            child1.add(parent1.get(i));
            child2.add(parent2.get(i));
        }
        for(int i=position+1;i<V;i++)
        {
            child1.add(parent2.get(i));
            child2.add(parent1.get(i));
        }
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        answer.add(child1);
        answer.add(child2);
        return answer;

    }

    static ArrayList<Integer> mutation1(ArrayList<Integer>individual, int V, int number_of_colors)
    {
        Random random = new Random();
        double probability = 0.4;
        double check = random.nextDouble(1);
        if(check<=probability)
        {
            int position = random.nextInt(V-1);
            individual.set(position, random.nextInt(number_of_colors));
        }
        return individual;
    }
    static ArrayList<Integer> mutation2(ArrayList<Integer>individual, int V, int number_of_colors)
    {
        Random random = new Random();
        double probability = 0.2;
        double check = random.nextDouble(1);
        if(check<=probability)
        {
            int position = random.nextInt(V-1);
            individual.set(position, random.nextInt(number_of_colors));
        }
        return individual;
    }

}
