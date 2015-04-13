
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Igor Freitas Universidade de Fortaleza (Matrícula: 1110546/7)
 * Sistemas Operacionais @Prof Eriko
 *
 */
public class BestFit {

    public int initial_processes; //Number of initial Processes
    public int memory_size; //Memory's size(User defines the size through GUI in bytes.
    public int memory_setted; //Amount of memory that have been setted by creating the blocks in function of initial processes.
    public int mid; // Id of the memory block. Starting from 0 and sequencial.
    public int pid; // Id of the process created. Starting from 0 and sequencial.
    public int list_size; // Size of the Linked list of Memory's blocks.
    public int processCount; //Count of processes created.
    public int abortedCount;
    public boolean garbage_collector;
    public MemoryCell trailer; //Trailer of the Linked list of memory's blocks.
    public MemoryCell header;  //Header of the Linked list of memory's blocks.
    public MemoryCell last;
    public ArrayList<MemoryCell> freeblocks = new ArrayList<>(); // Arraylist of free memory blocks.
    public ArrayList<MemoryCell> allocatedblocks = new ArrayList<>(); //Arraylist of occupied memory blocks.
    public ArrayList<Process> processes = new ArrayList<>(); //ArrayList of processes.

    public void setParameters(int num, int msize) { // Receive parameters  and initialize variables
        this.initial_processes = num;
        this.memory_size = msize;
        this.memory_setted = 0;
        this.trailer = null;
        this.header = null;
        this.last = null;
        this.mid = 0;
        this.pid = 0;
        this.list_size = 0;
        this.processCount = 0;
        this.abortedCount = 0;
        System.out.println("----------------------------------------------Processos Abortados-----------------------------------------------------------");
        this.TimerGC();
        this.TimerScheduler();
    }

    public void TimerGC() { // Periodic Timer, that executes every 5 seconds.
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GarbageCollector();
            }
        };
        Timer timer = new Timer(2000, actionListener);
        timer.start();

    }

    public void TimerScheduler() {
        ActionListener actionListener;
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Schedule();
            }
        };
        Timer timer = new Timer(3000, actionListener);
        timer.start();

    }

    public void GarbageCollector() { // When the method is called, searches the linked list of memory blocks for blocks with dead processes
        this.allocatedblocksList("Search", null, 0);
    }

    public int getlistSize() { // Returns the size of the Linked list of Memory's blocks.
        return this.list_size;
    }

    public int getMemorySize() { // Returns the size of memory in bytes, definited by the user.
        return this.memory_size;
    }

    public int getMemorySetted() { // Returns the Amount of memory that have been setted by creating the blocks in function of initial processes.
        return this.memory_setted;
    }

      public int getfreeMemory() { //Return the total of Free Memory in bytes.
        int free_memory = 0;
        MemoryCell aux = trailer;
        for (int i = 1; i <= list_size; i++) {
            if(aux.process("Get", null) == null ){
            free_memory = free_memory + aux.getSize();
            }
            aux = aux.getNext();
        }
        return free_memory;
    }

    public int getUsedMemory() { //Return the total of Used Memory in bytes.
        int used_memory = 0;
        MemoryCell aux = trailer;
        for (int i = 1; i <= list_size; i++) {
            used_memory = used_memory + aux.getsizeUsed();
            aux = aux.getNext();
        }
        return used_memory;
    }

    public synchronized void sortFreeBlocksID() { // Sort the array of freeblocks by M#ID
        Collections.sort(freeblocks, new Comparator<MemoryCell>() {
            @Override
            public int compare(MemoryCell m1, MemoryCell m2) {
                return (int) (m1.getId() - m2.getId());
            }
        });
    }

    public synchronized void sortFreeBlocksSize() { //Sort the arraylist of freeblocks by size, according to bestfit.
        Collections.sort(freeblocks, new Comparator<MemoryCell>() {
            @Override
            public int compare(MemoryCell m1, MemoryCell m2) {
                return (int) (m1.getSize() - m2.getSize());
            }
        });
    }

    public synchronized MemoryCell freeblocksList(String op, MemoryCell b, Process p) { //Synchronized Method to interact with the listof free blocks.
        if (op.equals(("Remove"))) { //If string op equals to "Remove", removes the memory block from the List of free blocks.
            this.freeblocks.remove(b);
            sortFreeBlocksID();
            this.sortFreeBlocksSize();
            return null;
        }
        if (op.equals(("Add"))) { //If string op equals to "Add", adds the memory block in the List of free blocks.
            this.freeblocks.add(b);
            sortFreeBlocksID();
            this.sortFreeBlocksSize();
            return null;
        }
        if (op.equals(("Search"))) { //Searches the list of free blocks for the first block that has the size equal or greater than the process size.
            for (int i = 0; i < this.freeblocks.size(); i++) {
                MemoryCell block = freeblocks.get(i);
                if (block.getSize() >= p.getSize()) {
                    sortFreeBlocksID();
                    this.sortFreeBlocksSize();
                    allocatedblocksList("Add", block, 0);
                    return block;
                }
            }

        }

        return null;
    }

    public synchronized void allocatedblocksList(String op, MemoryCell b, int index) { //Synchronized Method to interact with the list of allocated blocks.

        if (op.equals(("Remove"))) { //If string op equals to "Remove", removes the memory block from the List allocated blocks.
            this.allocatedblocks.remove(b);
        }
        if (op.equals(("Add"))) { //If string op equals to "Add", adds the memory block in the List of allocated blocks.
            this.allocatedblocks.add(b);
        }
        if (op.equals(("Search"))) { //Searches the list of allocated blocks for blocks with dead processes.
            MemoryCell aux;
            for (int i = 0; i < this.allocatedblocks.size(); i++) {
                aux = allocatedblocks.get(i);
                if (aux.process("Get", null) != null && aux.process("Get", null).isAlive() == false) {
                    aux.process("Set", null);
                    aux.setsizeUsed(0);
                    this.allocatedblocks.remove(aux);
                    this.freeblocksList("Add", aux, null);
                }
            }
        }
    }

    public synchronized Process processesList(String op, Process p, int index) { //Synchronized  method to interact with the arraylist of processes.
        if (op.equals(("Add"))) {
            this.processes.add(p);
        }
        if (op.equals(("Remove"))) {
            this.processes.remove(index);
        }
        return null;
    }

    public Process createProcess() { // Creates the process with all it's parameters: Pid, quantum, size.
        Random random = new Random();
        int cpu_time = 10 + (int) (Math.random() * 20);
        int process_size = (int) (Math.pow(2, (random.nextInt(6) + 5)));
        Process p = new Process(cpu_time, process_size, null);
        p.setName(String.valueOf(pid));
        this.pid++;
        this.processesList("Add", p, 0);
        return p;
    }

    public void Schedule() { // Scheduler
        Process p;
        MemoryCell m;
        int remaining;
        remaining = memory_size - memory_setted;
        if (this.processes.size() >= 1) {
            p = this.processes.get(0);
            m = this.freeblocksList("Search", null, p);
            if (m != null) {
                this.processes.remove(0);
                this.freeblocksList("Remove", m, null);
                this.allocatedblocksList("Add", m, 0);
                m.process("Set", p);
                m.setsizeUsed(p.getSize());
                p.start();
                last = m;
            }
            if (m == null) {
                if (remaining < 32 && remaining > 0) {
                    this.processes.remove(0);
                    System.out.println("Processo Abortado: " + p.toString());
                    this.abortedCount++;
                    memory_setted = memory_size;
                    this.createBlock(remaining);
                }
                if (this.memory_setted + p.getSize() <= this.memory_size) {
                    memory_setted = memory_setted + p.getSize();
                    m = this.createBlock(p.getSize());
                    this.processes.remove(0);
                    this.allocatedblocksList("Add", m, 0);
                    m.process("Set", p);
                    m.setsizeUsed(p.getSize());
                    p.start();
                    last = m;
                } else {
                    this.processes.remove(0);
                    System.out.println("Processo Abortado: " + p.toString());
                    this.abortedCount++;
                }

            }
        }
    }

    public MemoryCell createBlock(int size) {
        MemoryCell m = new MemoryCell(null, null, mid, size, 0, true, null);
        if (this.list_size == 0) {
            this.trailer = m;
        } else {
            this.header.setNext(m);
            m.setPrevious(this.header);
        }
        this.header = m;
        this.mid++;
        this.list_size++;
        return m;
    }

    public int Exit() {
        System.exit(0);
        return 0;
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        });
    }

}
