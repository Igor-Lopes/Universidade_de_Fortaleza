package filesystem;

import java.io.Serializable;

/**
 *
 * @author Igor
 */
public abstract class Header extends MemoryCell implements Serializable{

    public int firstblock;
    public int byteslenght;
    public int totalofblocks;
    public String filename;
    private static final long serialVersionUID = 1000L;

    public Header(int mid,int fblock, String fname, int tblocks, int bl) {
        this.firstblock = fblock;
        this.filename = fname;
        this.totalofblocks = tblocks;
        this.byteslenght = bl;

    }
    @Override
    public String toString(){
        return "FileName: " + filename + " | Blocks: " + totalofblocks;
        
    }
}
