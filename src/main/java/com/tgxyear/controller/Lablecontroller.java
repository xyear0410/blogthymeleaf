package com.tgxyear.controller;

import com.tgxyear.mapper.LabelViewMapper;
import com.tgxyear.mapper.Labelidmapper;
import com.tgxyear.mapper.Labelmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Lablecontroller {
    @Autowired
    Labelmapper labelmapper;
    @Autowired
    LabelViewMapper labelViewMapper;
    @Autowired
    Labelidmapper labelidmapper;

}
