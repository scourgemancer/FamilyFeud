import java.util.Stack;

/**
 * Makes mementos for the game as it progresses and keeps track of their order
 */
class Caretaker{
    private Stack<Memento> undos;
    private Stack<Memento> redos;

    private boolean canRedo;

    private GameGUI gui;

    Caretaker(GameGUI gui){
        this.gui = gui;
        canRedo = false;
        undos = new Stack<>();
        redos = new Stack<>();
    }

    void save(){
        undos.push(new Memento(gui));
        canRedo = false;
        if(!redos.empty()) redos = new Stack<>();
    }

    void undo(){
        if(undos.empty()) return;
        redos.push(new Memento(gui));
        undos.pop().reinstate();
        canRedo = true;
    }

    void redo(){
        if(canRedo && !redos.empty()){
            undos.push(new Memento(gui));
            redos.pop().reinstate();
        }

    }
}
