package jpl.ch20.ex04;

import static org.junit.Assert.*;

import java.io.FilterReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class LineReaderTest {

    @Test
    public void testReadLine1() {
        LineReader lineReader = null;
        String[] strings = {"abc", "def"};
        String seperator = "\n";
        
        try {
            lineReader = new LineReader(new StringReader(String.join(seperator, strings)));
            
            String str;
            int count = 0;
            while((str = lineReader.readLine()) != null) {
                assertTrue(str.equals(strings[count]));
                count++;
            }
            assertTrue(count == 2);
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReadLine2() {
        LineReader lineReader = null;
        String[] strings = {"abc", "def"};
        String seperator = "\r";
        
        try {
            lineReader = new LineReader(new StringReader(String.join(seperator, strings)));
            
            String str;
            int count = 0;
            while((str = lineReader.readLine()) != null) {
                assertTrue(str.equals(strings[count]));
                count++;
            }
            assertTrue(count == 2);
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReadLine3() {
        LineReader lineReader = null;
        String[] strings = {"abc", "def"};
        String seperator = "\r\n";
        
        try {
            lineReader = new LineReader(new StringReader(String.join(seperator, strings)));
            
            String str;
            int count = 0;
            while((str = lineReader.readLine()) != null) {
                assertTrue(str.equals(strings[count]));
                count++;
            }
            assertTrue(count == 2);
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReadOneWord() {
        LineReader lineReader = null;
        String word = "abc";
        
        try {
            lineReader = new LineReader(new StringReader(word));
            
            String str;
            int count = 0;
            while((str = lineReader.readLine()) != null) {
                assertTrue(str.equals(word));
                count++;
            }
            assertTrue(count == 1);
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReadOneWordAndLineSeperator() {
        LineReader lineReader = null;
        String word = "abc";
        
        try {
            lineReader = new LineReader(new StringReader(word + "\n"));
            
            String str;
            int count = 0;
            while((str = lineReader.readLine()) != null) {
                assertTrue(str.equals(word));
                count++;
            }
            assertTrue(count == 1);
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReadOnlyLineSeperator() {
        LineReader lineReader = null;
        String word = "\n";
        
        try {
            lineReader = new LineReader(new StringReader(word));
            
            String str;
            int count = 0;
            while((str = lineReader.readLine()) != null) {
                assertTrue(str.equals(word));
                count++;
            }
            assertTrue(count == 0);
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testReadEmptyWord() {
        LineReader lineReader = null;
        String word = "";
        
        try {
            lineReader = new LineReader(new StringReader(word));
            
            String str;
            int count = 0;
            while((str = lineReader.readLine()) != null) {
                assertTrue(str.equals(word));
                count++;
            }
            assertTrue(count == 0);
        } catch (IOException e) {
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
