import java.util.Stack;

/**
 * Makes mementos for the game as it progresses and keeps track of their order
 */
public class Caretaker{
    private Stack<Memento> undos;
    private Stack<Memento> redos;

    private boolean canRedo;

    private GameGUI gui;

    Caretaker(GameGUI gui){
        this.gui = gui;
        canRedo = false;
        undos = new Stack<Memento>();
        redos = new Stack<Memento>();
    }

    public void save(){
        undos.push(new Memento(gui));
        canRedo = false;
        if(!redos.empty()) redos = new Stack<Memento>();
    }

    public void undo(){
        redos.push(new Memento(gui));
        undos.pop().reinstate();
        canRedo = true;
    }

    public void redo(){
        if(canRedo && !redos.empty()){
            undos.push(new Memento(gui));
            redos.pop().reinstate();
        }

    }
}
