package com.example.threadtest;

import com.example.Shop;

import java.util.ArrayList;
import java.util.List;

enum Level {
    LOW("Low Level", 30) {
        @Override
        public double getDistance() {
            return 30.0;
        }
    },
    MEDIUM("Medium Level", 15) {
        @Override
        public double getDistance() {
            return 15.0;
        }
    },
    HIGH("High Level", 7) {
        @Override
        public double getDistance() {
            return 7.0;
        }
    },
    URGENT("Urgent Level", 1) {
        @Override
        public double getDistance() {
            return 1.0;
        }
    };

    private int levelValue;
    private String description;

    private Level(String description, int levelValue) {
        this.description = description;
        this.levelValue = levelValue;
    }

    public int getLevelValue() {
        return levelValue;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public abstract double getDistance();
}

public class Main {
    public static void main(String[] args) {
        for (Level s : Level.values()) {
            String name = s.name();
            String desc = s.toString();
            int ordinal = s.ordinal();
            int levelValue = s.getLevelValue();
            double distance = s.getDistance();
            System.out.println("name=" + name + ",  description=" + desc
                    + ",  ordinal=" + ordinal + ", levelValue=" + levelValue
                    + ", distance=" + distance);
        }


        List<? super Integer> list = new ArrayList<>();
        list.add(new Integer(1));
        Integer o = (Integer) list.get(0);

        Shop<? super Integer> shop = new Shop<>();


    }
}