package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args) throws IOException {
        Schema schema = new Schema(1, "com.example.dllo.mirror.db");

        // 首页没网时收藏有网的时候的数据的表
        Entity homeData = schema.addEntity("HomeData");
        homeData.addIdProperty().primaryKey().autoincrement();

        homeData.addStringProperty("key");
        homeData.addStringProperty("value");

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java/");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
