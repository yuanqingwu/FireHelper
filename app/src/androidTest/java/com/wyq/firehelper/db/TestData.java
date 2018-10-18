package com.wyq.firehelper.db;

import com.wyq.firehelper.developkit.room.entity.UserEntity;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static final UserEntity USER_ENTITY = new UserEntity(0,"zhang","san");
    public static final UserEntity USER_ENTITY1 = new UserEntity(1,"李","四");

    public static final List<UserEntity> USER_ENTITIES = Arrays.asList(USER_ENTITY,USER_ENTITY1);
}
