package jpl.ch23.ex01;

import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

public class CommandPrompt {

    public static void main(String[] args) {
        try {
            userProg("cmd.exe /c dir");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Process userProg(String cmd) throws IOException {
        Process proc = Runtime.getRuntime().exec(cmd);
        plugTogether(System.in, proc.getOutputStream());
        plugTogether(System.out, proc.getInputStream());
        return proc;
    }
    
    private static void plugTogether(InputStream parent, OutputStream child) {
        PipedOutputStream pipedOutputStream = null;
        PipedInputStream pipedInputStream = null;
        InputThread inputThread = null;
        OutputThread outputThread = null;
        
        try {
            pipedOutputStream = new PipedOutputStream();
            pipedInputStream = new PipedInputStream(pipedOutputStream);

            inputThread = new InputThread(pipedOutputStream, parent);
            inputThread.start();
            outputThread = new OutputThread(pipedInputStream, child);
            outputThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void plugTogether(OutputStream parent, InputStream child) {
        PipedOutputStream pipedOutputStream = null;
        PipedInputStream pipedInputStream = null;
        InputThread inputThread = null;
        OutputThread outputThread = null;
        
        try {
            pipedOutputStream = new PipedOutputStream();
            pipedInputStream = new PipedInputStream(pipedOutputStream);

            inputThread = new InputThread(pipedOutputStream, child);
            inputThread.start();
            outputThread = new OutputThread(pipedInputStream, parent);
            outputThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static class InputThread extends Thread {
      private PipedOutputStream pipedOutputStream;
      private InputStream input;

      public InputThread(PipedOutputStream pipedOutputStream, InputStream input) {
        this.pipedOutputStream = pipedOutputStream;
        this.input = input;
      }
      
      public void run() {
        try {
          int c = 0;
          while(true) {
              c = input.read();
              if (c == -1) {
                  break;
              }
              pipedOutputStream.write(c);
          }
        } catch(IOException e) {
          e.printStackTrace();
        } finally {
            try {
                if (pipedOutputStream != null) {
                    pipedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
      }
    }

    static class OutputThread extends Thread {
        private PipedInputStream pipedInputStream;
        private OutputStream out;

        public OutputThread(PipedInputStream pipedInputStream, OutputStream out) {
          this.pipedInputStream = pipedInputStream;
          this.out = out;
        }
        
        public void run() {
          try {
            int c = 0;
            while(true) {
                c = pipedInputStream.read();
                if (c == -1) {
                    break;
                }
                out.write(c);
            }
          } catch(IOException e) {
            e.printStackTrace();
          } finally {
              try {
                  if (pipedInputStream != null) {
                      pipedInputStream.close();
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
      }

    public String[] dir(String dir, String ops) throws Exception {
        try {
            String[] cmdArray = {"cmd.exe", "/c", "dir", ops, dir};
            Process child = Runtime.getRuntime().exec(cmdArray);
            InputStream lsOut = child.getInputStream();
            InputStreamReader r = new InputStreamReader(lsOut);
            BufferedReader in = new BufferedReader(r);
            
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
            if (child.waitFor() != 0) {
                throw new Exception();
            }
            return lines.toArray(new String[lines.size()]);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
