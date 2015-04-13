package filesystem;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Igor
 */
public class ContigousAllocation implements Serializable {
    public int capacity; // Capacidade de armazenamento
    public int numberofblocks; // Total de Blocos da mídia.
    public int blocksize; // Tamanho do bloco.
    public int mid = 0; // ID do bloco.
    public ArrayList<MemoryCell> blocks = new ArrayList<>(); //Lista de Blocos.
    public SuperBlock s;
  public void installFile(int c, int b) throws IOException {
System.out.println("Instalando Mídia de Armazenamento");
        this.capacity = c * 1024; // Transforma MB para KB
        blocksize = b; // Tamanho do bloco em KB
        this.numberofblocks = capacity / b; // Calcula o número de blocos que o sistema vai possuir.
        this.s = new SuperBlock (c,b) {};
        s.numberofblocks = numberofblocks;

        s.freeblocks = new boolean[numberofblocks]; //Seta tamanho do array de status dos blocos.
        this.blocks.add(s);

        s.freeblocks[0] = true;
        for (int i = 1; i < s.freeblocks.length; i++) {
            s.freeblocks[i] = false;
        }

        for (int i = 1; i < numberofblocks; i++) {
            MemoryCell m;
            m = new MemoryCell() {
            };
            this.blocks.add(m);
            this.mid++;
        }
        System.out.println(" Blocos: " + numberofblocks + " Blocos livres:" + getFreeBlocks());
      //  System.out.println(s.linkedallocation.size());
        this.rewriteFile();
    }

     public void rewriteFile() throws IOException {

        System.out.println("---------------------------- Rewriting Storage ----------------------------");
        ObjectOutputStream saida = null; //ok
        String nomeArquivo = "C:\\Users\\Igor\\Desktop\\Storage.bin"; //ok
        saida = new ObjectOutputStream(new FileOutputStream(nomeArquivo, false));//ok - false
        File f = new File(nomeArquivo);
        f.delete();
        MemoryCell m;
        for (int i = 0; i < this.numberofblocks; i++) {
            m = this.blocks.get(i);
            saida.writeObject(m);

        }
        saida.reset();
        this.blocks.clear();
    }
  public void readFile() throws Exception {
        this.blocks.clear();
        System.out.println("---------------------------- Reading Storage ----------------------------");
        ObjectInputStream entrada = null;
        String nomeArquivo = "C:\\Users\\Igor\\Desktop\\Storage.bin";
        entrada = new ObjectInputStream(new FileInputStream(nomeArquivo));
        SuperBlock superblock = (SuperBlock) entrada.readObject();
        this.blocks.add(superblock);
        this.s = (SuperBlock) this.blocks.get(0);
        this.numberofblocks = s.totalofblocks;
        this.blocksize = s.block_size;
        this.numberofblocks = s.totalofblocks;
        for (int i = 1; i < this.numberofblocks; i++) {
            MemoryCell m = (MemoryCell) entrada.readObject();
            this.blocks.add(m);
        }
        entrada.close();

        System.out.println(blocks.size());
      //  File f = new File(nomeArquivo);
      //  f.delete();
    }

    public int getFreeBlocks() {
        int count = 0;
        for (int i = 0; i < s.freeblocks.length; i++) {
            if (s.freeblocks[i] == false) {
                count++;
            }
        }
        return count;
    }

    public int getFirstFreeBlock() {
        for (int i = 0; i < s.freeblocks.length; i++) {
            if (s.freeblocks[i] == false) {
                return i;
            }
        }
        return -1;
    }

    public void storeFile(String path) throws FileNotFoundException, IOException {
                try {
            readFile();
        } catch (Exception ex) {
            Logger.getLogger(ContigousAllocation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //------------------------------------------------------------------------
        int number_blocks;
        int freeblocks;
        int firstfreeblock;
        int nextdatablock;
        SuperBlock s;
        byte[] bFile;
        File file;
        String filename;
        //------------------------------------------------------------------------

        file = new File(path);
        filename = file.getName();
        bFile = new byte[(int) file.length()];
        firstfreeblock = this.getFirstFreeBlock();
        freeblocks = this.getFreeBlocks();
        nextdatablock = firstfreeblock + 1;
        number_blocks = this.getNumberOfBlocks((int) file.length());
        s = (SuperBlock) this.blocks.get(0);
        //------------------------------------------------------------------------
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(bFile);
        dis.close();
        //------------------------------------------------------------------------

       if (number_blocks + 1 > freeblocks) {
            System.out.println("-------------- Aborted - File is too large - Xx Out Of Memory xX --------------");
        } else {
            s.FAT.put(filename, firstfreeblock);
            Header h = new Header(firstfreeblock, nextdatablock, filename, number_blocks, (int) file.length()) {
            };
            this.blocks.set(firstfreeblock, h);
            this.s.freeblocks[firstfreeblock] = true;
            int lastindex = 0;
            for (int i = nextdatablock; i < nextdatablock + number_blocks; i++) {
                DataBlock d = new DataBlock(blocksize) {
                };
//                MemoryCell m = this.blocks.get(i); // VERIFICA SE O BLOCO REALMENTE ESTÁ GENERICO E DESALOCADO.
                this.blocks.set(i, d);
                this.s.freeblocks[i] = true;
                for (int j = 0; j < d.array.length; j++) {
                    if (lastindex < bFile.length) {
                        d.array[j] = bFile[lastindex];
                    }
                    lastindex++;
                }
            }
            System.out.println("File saved successfully !");
            System.out.println(" Blocos: " + numberofblocks + " Lista:" + blocks.size() + " Blocos livres:" + getFreeBlocks());
        }
        //------------------------------------------------------------------------
             
        rewriteFile();
    }

    public void removeFile(String filename) throws IOException {
                  try {
            readFile();
        } catch (Exception ex) {
            Logger.getLogger(ContigousAllocation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        System.out.println("--------------------------------------- Deleting File ---------------------------------------");

        //------------------------------------------------------------------------ //ok
        File f;
        SuperBlock s;
        int header;
        int nextdatablock;
        int lastindex;
        int number_blocks;
        int lenght;
        Header h;

        //------------------------------------------------------------------------ //ok
        s = (SuperBlock) this.blocks.get(0);
        header = s.FAT.get(filename);
       
        h = (Header) this.blocks.get(header);
        number_blocks = h.totalofblocks;
        f = new File(filename);
        lastindex = 0;
        lenght = h.byteslenght;
        s.FAT.remove(filename);
        // System.out.println(header + " - " + filename);
       
        //------------------------------------------------------------------------ //ok
        for (int i = header; i <= header + number_blocks; i++) {
            this.s.freeblocks[i] = false;
            MemoryCell m = null; {
            }
            this.blocks.set(i, m);
        }
        //------------------------------------------------------------------------ 
        int num_blocks = h.totalofblocks;
        Header h_relocate;
        MemoryCell clear = null; {
        }
        Collection<Integer> header_index = s.FAT.values();

        for (int i = header; i < blocks.size(); i++) {
            if (s.freeblocks[i] == false) {
                outerloop:
                for (int j = i + 1; j < blocks.size(); j++) {
                    if (s.freeblocks[j] == true) {
                        if (header_index.contains(j)) {
                            header_index.remove(j);
                            Header h_aux = (Header) this.blocks.get(j);
                            String name = h_aux.filename;
                            SuperBlock saux = (SuperBlock) this.blocks.get(0);
                            saux.FAT.remove(name);
                            saux.FAT.put(name, i);
                            this.blocks.set(0, saux);
                            this.blocks.set(i, this.blocks.get(j));
                            s.freeblocks[i] = true;
                            this.blocks.set(j, clear);//
                            s.freeblocks[j] = false;
                            break outerloop;

                        }
                        if (header_index.contains(j) == false) {
                            DataBlock d_aux = (DataBlock) this.blocks.get(j);
                            this.blocks.set(i, this.blocks.get(j));
                            s.freeblocks[i] = true;
                            this.blocks.set(j, clear); //
                            s.freeblocks[j] = false;
                            break outerloop;
                        }

                    }
                }
            }

        }
        this.rewriteFile();
        //------------------------------------------------------------------------
        System.out.println("File was removed successfully !");
        System.out.println(" Blocos: " + numberofblocks + " Lista:" + blocks.size() + " Blocos livres:" + getFreeBlocks());
                
    }

    public void restoreFile(String filename) throws FileNotFoundException, IOException {
                    try {
            readFile();
        } catch (Exception ex) {
            Logger.getLogger(ContigousAllocation.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("--------------------------------------- Recovering File ---------------------------------------");

        //------------------------------------------------------------------------
        File f;
        byte[] bFile;
        SuperBlock s;
        int header;
        int nextdatablock;
        int lastindex;
        int number_blocks;
        int lenght;
        Header h;
        //------------------------------------------------------------------------

        s = (SuperBlock) this.blocks.get(0);
        header = s.FAT.get(filename);
        nextdatablock = header + 1;
        h = (Header) this.blocks.get(header);
        number_blocks = h.totalofblocks;
        f = new File(filename);
        lastindex = 0;
        lenght = h.byteslenght;
        bFile = new byte[lenght];
        System.out.println("HEADER " + header);
        System.out.println("BLOCOS " + number_blocks);
        //------------------------------------------------------------------------
        for (int i = nextdatablock; i < nextdatablock + number_blocks; i++) {
            DataBlock d = (DataBlock) this.blocks.get(i);
            for (int j = 0; j < d.array.length; j++) {
                if (lastindex < bFile.length) {
                    bFile[lastindex] = d.array[j];
                }
                lastindex++;
                if (lastindex >= bFile.length) {
                    break;
                }
            }
        }

        //------------------------------------------------------------------------
        File outFile = new File("C:\\Users\\Igor\\Desktop\\" + "Recovery -" + filename);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile));
        dos.write(bFile, 0, bFile.length);
        dos.close();
        System.out.println("File was recovered successfully !");
        System.out.println(" Blocos: " + numberofblocks + " Lista:" + blocks.size() + " Blocos livres:" + getFreeBlocks());
      //   this.rewriteFile();
    }

    public int getNumberOfBlocks(int f_size) {
        int file_size = f_size;
        int count = 0;
        while (file_size != 0) {
            if (file_size - blocksize * 1024 > 0) {
                file_size = file_size - blocksize * 1024;
                count++;
            }
            if (file_size - blocksize * 1024 < 0) {
                file_size = 0;
                count++;
            }
            if (file_size - blocksize * 1024 == 0) {
                file_size = 0;
                count++;
            } else {

            }
        }
        System.out.println("Blocos Necessários: " + count);
        return count;
    }

    public static void main(String[] args) throws IOException {
        String op;
        Scanner s = new Scanner(System.in);
        ContigousAllocation ca = new ContigousAllocation();
       // ca.installFile(1024, 1024);
     /*  while (true) {
            
            System.out.println(" Store / Remove/ Restore ");
            op = s.nextLine();
            if (op.equalsIgnoreCase("Store")) {
                System.out.print("File Path: ");
                String path = s.nextLine();
                ca.storeFile(path);
                System.out.println(ca.s.FAT.entrySet());
            }
            if (op.equalsIgnoreCase("Restore")) {
                System.out.print("File Name: ");
                String path = s.nextLine();
                ca.restoreFile(path);
                System.out.println(ca.s.FAT.entrySet());
            }
            if (op.equalsIgnoreCase("Remove")) {
                System.out.print("File Name: ");
                String path = s.nextLine();
                ca.removeFile(path);
                System.out.println(ca.s.FAT.entrySet());
            }

        } */

    } 
}
