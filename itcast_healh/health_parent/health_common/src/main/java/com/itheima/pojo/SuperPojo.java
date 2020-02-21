package com.itheima.pojo;

import java.io.Serializable;
import java.util.TreeMap;


    public class SuperPojo extends TreeMap<String, Object> implements Serializable {


        /**
         *
         */
        private static final long serialVersionUID = 1L;


        public SuperPojo setProperty(String key, Object value) {

            this.put(key, value);

            return this;
        }

    }


