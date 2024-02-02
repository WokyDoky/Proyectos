package HW2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class humanPTest {

    @Test
    void getString() {

        //Using a ByteArrayInputStream is different for me since I am using letters
        
        var human = new humanP(1, "X");


        assertEquals(Arrays.toString(new String[] {"4"}), Arrays.toString(human.getString("4")));
        assertEquals(Arrays.toString(new String[] {"2","a"}), Arrays.toString(human.getString("2,a")));


        //Checks if the given string is too small.
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {
                    human.getString("1");
                });
        //Same for bigger.
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {
                    human.getString("1,2,1");
                });
    }
}