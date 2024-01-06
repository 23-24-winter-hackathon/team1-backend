package com.example.demo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<Food> {

    private static final long serialVersionUID = -1044044882L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFood food = new QFood("food");

    public final NumberPath<Integer> apiIndex = createNumber("apiIndex", Integer.class);

    public final StringPath foodName = createString("foodName");

    public final EnumPath<com.example.demo.domain.enums.FoodType> foodType = createEnum("foodType", com.example.demo.domain.enums.FoodType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgSrc = createString("imgSrc");

    public final StringPath ingredients = createString("ingredients");

    public final QNutrition nutrition;

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public final EnumPath<com.example.demo.domain.enums.WayToCook> wayToCook = createEnum("wayToCook", com.example.demo.domain.enums.WayToCook.class);

    public QFood(String variable) {
        this(Food.class, forVariable(variable), INITS);
    }

    public QFood(Path<? extends Food> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFood(PathMetadata metadata, PathInits inits) {
        this(Food.class, metadata, inits);
    }

    public QFood(Class<? extends Food> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nutrition = inits.isInitialized("nutrition") ? new QNutrition(forProperty("nutrition")) : null;
    }

}

