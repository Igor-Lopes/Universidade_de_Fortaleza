
/**
 *
 * @author Igor
 */
public class MemoryCell {

    public int mid = 0;
    public int size = 0;
    public int size_used = 0;
    public boolean status = false;
    public Process allocated = null;
    public MemoryCell next = null;
    public MemoryCell previous = null;
    public int arraylist;
    public int index;

    public MemoryCell(MemoryCell n, MemoryCell p, int id, int s, int size_u, boolean st, Process a) {
        this.mid = id;
        this.size = s;
        this.size_used = size_u;
        this.status = st;
        this.allocated = a;
        this.next = n;
        this.previous = p;
        this.arraylist = 0;
        this.index = 0;
    }

    public int getId() {
        return this.mid;
    }

    public void setProcess(Process a) {
        this.allocated = a;
    }

    public void setArrayList(int a) {
        this.arraylist = a;
    }

    public int getArrayList() {
        return this.arraylist;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getIndex() {
        return this.index;
    }

    public synchronized Process process(String op, Process p) {
        switch (op) {
            case "Get":
                return allocated;
            case "Set":
                this.allocated = p;
                break;
        }
        return this.allocated;
    }

    public void setsizeUsed(int size_u) {
        this.size_used = size_u;
    }

    public int getSize() {
        return this.size;
    }

    public int getsizeUsed() {
        return this.size_used;
    }

    public void setStatus(boolean st) {
        this.status = st;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setNext(MemoryCell n) {
        this.next = n;
    }

    public MemoryCell getNext() {
        return this.next;
    }

    public void setPrevious(MemoryCell p) {
        this.previous = p;
    }

    public MemoryCell getPrevious() {
        return this.previous;
    }

    @Override
    public String toString() {
        Process p = process("Get", null);
        if (p != null) {
            return "[" + "M#" + getId() + "  | " + getsizeUsed() + "/" + getSize() + " Bytes" + "]" + "|" + " [P#" + p.getName() + "]" + "|" + " RemainingTime: " + p.gettimeCount() + " sec";
        }
        if (p == null) {
            return "[" + "M#" + getId() + "  | " + getsizeUsed() + "/" + getSize() + " Bytes" + "]";
        }
        return "ERROR";
    }
}
