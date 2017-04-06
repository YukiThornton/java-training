package interpret;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class JTextAreaStream extends OutputStream {

    private JTextArea textArea;
    private ByteArrayOutputStream stream;
    
    public JTextAreaStream(JTextArea textArea) {
        this.textArea = textArea;
        this.stream = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        stream.write(b);
    }
    
    @Override
    public void flush() throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                textArea.append(stream.toString());
                stream.reset();
            }
        });
    }

}
