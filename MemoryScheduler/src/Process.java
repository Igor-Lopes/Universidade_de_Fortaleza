
/**
 *
 * @author Igor
 */
public class Process extends Thread {

    public long current_time;
    public long total_time;
    public int time_count;
    public int cpu_time;
    public int process_size;
    public MemoryCell adress;

    public Process(int t, int p_size, MemoryCell a) {
        this.cpu_time = t;
        this.process_size = p_size;
        this.time_count = cpu_time;
        this.adress = a;
    }
    
    public void setAdress(MemoryCell a){
        this.adress = a;
    }
    
    public MemoryCell getAdress(){
        return this.adress;
    }

    public int getTime() {
        return this.cpu_time;
    }

    public int gettimeCount() {
        return this.time_count;
    }

    public int getSize() {
        return this.process_size;
    }

    public void run() {

        current_time = (long) (System.currentTimeMillis());
        total_time = current_time + (cpu_time * 1000);
        while (time_count > 0) {
            current_time = System.currentTimeMillis();
            time_count--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            {
                
            }
        }
    }

    @Override
    public String toString() {
            return "[P#" + getName() + "| " + getSize() + "Bytes]|" + " CPU Time: " + getTime() + " sec |";

    }
}
