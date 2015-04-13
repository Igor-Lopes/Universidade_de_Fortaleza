
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Igor Freitas Universidade de Fortaleza (Matrícula: 1110546/7)
 * Sistemas Operacionais @Prof Eriko
 *
 */
public class MergeFit {

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
    public ArrayList<Process> processes = new ArrayList<>();
    public ArrayList<MemoryCell> toGui = new ArrayList<>();

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
        this.blocksList("Merge", null, null, 0);
        MemoryCell aux = trailer;
        for (int i = 1; i < list_size; i++) {
            System.out.println();
            System.out.println();
            System.out.println("=================================================");
            System.out.println(aux.toString());
            System.out.println("=================================================");
            aux = aux.getNext();
        }
    
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

    public synchronized MemoryCell blocksList(String op, MemoryCell b, Process p, int size) { //Synchronized Method to interact with the listof free blocks.
        MemoryCell aux;
        if (op.compareTo("Add") == 0) {
            MemoryCell m = new MemoryCell(null, null, mid, size, 0, false, null);
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

        if (op.compareTo("Search") == 0) {
            aux = trailer;
            for (int i = 1; i <= list_size; i++) {
                int remaining = aux.getSize() - aux.getsizeUsed();
                if (aux.getStatus() == true && remaining >= p.getSize()) {
                    aux.size = aux.size - p.getSize();
                    MemoryCell aux_next = null;
                    if (aux.getNext() != null) {
                        MemoryCell newblock = new MemoryCell(aux, aux.getNext(), 0, p.getSize(), p.getSize(), false, null);
                        aux.getNext().setPrevious(newblock);
                        aux.setNext(newblock);
                        this.list_size++;
                        this.mid = 0;
                        MemoryCell aux3 = trailer;
                        for (int j = 1; j <= list_size; i++) {
                            aux3.mid = mid;
                            mid++;
                            aux3 = aux3.getNext();
                        }
                        return newblock;
                    } else {
                        MemoryCell aux4 = trailer;
                        MemoryCell newblock = new MemoryCell(aux, null, 0, p.getSize(), p.getSize(), false, null);
                        this.header = newblock;
                        aux.setNext(newblock);
                        for (int j = 1; j <= list_size; i++) {
                            aux4.mid = mid;
                            mid++;
                            aux4 = aux4.getNext();
                        }
                        return newblock;
                    }
                }
                if (aux.getStatus() == false && aux.getSize() >= p.getSize()) {
                    return aux;

                }

            }

        }

        if (op.compareTo("Merge") == 0) {
            aux = trailer;
            for (int i = 1; i <= list_size; i++) {
                if (aux.getNext() != null) {
                    MemoryCell aux2 = aux.getNext();
                    if (aux.getStatus() == true && aux2.getStatus() == true) {
                        aux.size = aux.getNext().getSize();
                        aux2.setPrevious(null);
                        aux.setNext(aux2.getNext());
                        this.list_size--;
                        this.mid = 0;
                        MemoryCell block = trailer;
                        for (int j = 1; j <= list_size; i++) {
                            block.mid = mid;
                            mid++;
                            block = block.getNext();
                        }
                        aux2.setNext(null);
                    }

                }
                aux = aux.getNext();
            }

        }

        return null;
    }

    public synchronized Process processesList(String op, Process p, int index) {
        if (op.compareTo("Add") == 0) {
            this.processes.add(p);
        }
        if (op.compareTo("Remove") == 0) {
            this.processes.remove(index);
        }
        if (op.compareTo("Get") == 0) {
            return this.processes.get(index);
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

    public void Schedule() {
        Process p;
        MemoryCell m;
        int remaining;
        remaining = memory_size - memory_setted;
        if (this.processes.size() >= 1) {
            p = this.processes.get(0);
            m = this.blocksList("Search", null, p, 0);
            if (m != null) {
                this.processes.remove(0);
                m.process("Set", p);
                m.setsizeUsed(p.getSize());
                p.start();
                m.setStatus(true);
                last = m;
            }
            if (m == null) {
                if (remaining < 32 && remaining > 0) {
                    this.processes.remove(0);
                    //System.out.println("Processo Abortado: " + p.toString());
                    this.abortedCount++;
                    memory_setted = memory_size;
                    this.blocksList("Add", null, null, remaining);
                }
                if (this.memory_setted + p.getSize() <= this.memory_size) {
                    memory_setted = memory_setted + p.getSize();
                    m = this.blocksList("Add", null, null, p.getSize());
                    this.processes.remove(0);
                    m.process("Set", p);
                    m.setsizeUsed(p.getSize());
                    p.start();
                    m.setStatus(true);
                    last = m;
                } else {
                    this.processes.remove(0);
                    // System.out.println("Processo Abortado: " + p.toString());
                    this.abortedCount++;
                }

            }

        }
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
