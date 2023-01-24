import java.util.HashMap;
import java.util.Map;

public class Environment {
    final Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    Environment(){
        enclosing = null;
    }

    Environment(Environment enclosing){
        this.enclosing = enclosing;
    }

    Object get(Token name){
        String lexeme = name.lexeme;
        if(values.containsKey(lexeme)){
            return values.get(lexeme);
        }

        if(enclosing != null) return  enclosing.get(name);

        throw new RuntimeError(name, "Undefined variable '" + lexeme + "'.");
    }

    void assign(Token name, Object value){
        if(values.containsKey(name.lexeme)){
            values.put(name.lexeme, value);
            return;
        }

        if(enclosing != null){
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    void define(String name, Object value){
        values.put(name, value);
    }
}
