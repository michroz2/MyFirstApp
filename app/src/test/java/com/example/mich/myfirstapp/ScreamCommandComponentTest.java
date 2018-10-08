package com.example.mich.myfirstapp;

import org.junit.Assert;
import org.junit.Test;

public class ScreamCommandComponentTest {

    /**
     * Метод должеен при получении правильной последовательности сигналов задетектировать "команду" Ку-ку.
     * следует проверить на фэйковые и реальные данные.
     */
    @Test
    public void nextValue() {
        int[] sequenceReal = { // реальные данные Громкости,  полученные с микрофона дивайса при наложении на шум мотора крика Ку-Ку.
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 781, 2356, 2844, 5576, 9008, 5984, 10885, 11762, 13291, 15120, 9655, 13652, 8532, 14358, 9168, 10999, 10374, 10217, 11470, 10691, 11118, 11011, 9441, 10686, 11131, 12398, 8233, 10974, 11212, 11876, 11416, 8203, 11065, 7813, 9604, 9111, 10288, 9307, 9009, 8653, 9370, 10958, 7675, 8538, 7552, 10933, 8121, 9632, 11146, 10519, 8438, 7598, 8605, 7916, 7247, 9807, 7528, 8280, 7895, 8761, 8953, 8823, 10297, 4590, 3303, 11948, 18914, 19733, 28267, 21089, 5775, 5255, 7003, 6236, 12836, 26031, 22687, 26664, 27062, 29116, 27723, 8112, 5328, 7158, 6965, 7898, 6945, 6775, 9571, 7516, 7223, 7417, 8542, 8005, 7211, 8221, 6343, 7041, 8725, 6619, 9648, 10414, 8803, 10611, 9843, 10680, 12400, 11934, 9816, 9790, 9705, 8657, 6375, 6406, 4628, 12668, 17961, 22022, 28165, 25395, 10956, 7313, 6858, 4110, 18734, 27899, 26334, 28635, 29216, 27855, 26915, 23720, 17596, 7960, 4809, 6749, 4798, 6303, 5987, 4583, 7164, 4781, 6587, 6142, 8012, 5780, 5660, 5491, 6189, 6833, 6135, 8127, 8078, 5504, 8155, 5506, 7595, 10727, 8036, 8496, 10225, 11152, 11515, 9902, 10399, 12073, 12457, 11682, 8574, 13341, 11640, 13157, 10760, 15364, 11120, 14846, 14538, 13967, 9632, 8789, 9219, 8249, 5288, 13293, 19495, 20453, 23490, 28660, 27788, 11392, 5391, 5320, 4277, 20698, 26054, 22691, 25678, 26425, 28072, 25877, 27845, 25574, 11774, 7771, 5632, 7637, 8555, 10039, 7193, 6222, 6482, 6867, 7061, 7150, 10201, 8100, 7276, 9094, 8623, 7849, 8157, 6523, 7386
        };
        ScreamCommandComponent screamCommandComponent = new ScreamCommandComponent();

        for (int i = 0; i < sequenceReal.length; i++) {
            screamCommandComponent.nextValue(sequenceReal[i]);
        }
        Assert.assertTrue(screamCommandComponent.isCommandDetected());

    }
}