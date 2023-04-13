package restaurant.common.transfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class Request implements Serializable{
    
    private Operation operation;
    private Object argument;
    private List<Object> arguments;

    public Request(Operation operation) {
        this.operation = operation;
        this.arguments = new ArrayList<>();
    }

    public Request(Operation operation, Object argument) {
        this.operation = operation;
        this.argument = argument;
        this.arguments = new ArrayList<>();
    }

    public Request(Operation operation, List<Object> arguments) {
        this.operation = operation;
        this.arguments = arguments;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }
    
    

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }
    
    public void addArgument(Object argument){
        arguments.add(argument);
    }
    
}
