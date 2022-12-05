package io.github.nmahdi.JunoCore.generation;

public enum GeneratorType {
    Ore("ore"),
    Tree("tree"),
    Plant("plant"),//Also used for flowers
    ;

    String id;

    GeneratorType(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static GeneratorType getType(String id){
        for(GeneratorType type : GeneratorType.values()){
            if(type.getId().equals(id)) return type;
        }
        return null;
    }
}
