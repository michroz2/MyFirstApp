package com.example.mich.myfirstapp;

/**
 * Этот компонент определяет наличие "голосовой команды" ("Ку-ку!", "Хо-хо!" или "Э-гей!") в потоке значений громкости записываемого звука.
 * Он обрабатывает значения грмкости и сравнивает результат с требуемыми значениями.
 * Для простоты считается, что значения идут с частотой 1/33мс (~30 Гц)
 */
public class ScreamCommandComponent {
    private static int[][] qqPattern = {{4, -4, 4, -4}, {8, -8, 10, -12}}; // последовательность мин и мах подходящих длин участков значений общего знака +/-/+/-
    private int prefix = 10; // мин допустимое время отсутствия сигнала до начала последовательности (не использ.)
    private int postfix = 10; // мин допустимое время отсутствия сигнала после конца последовательности
    private int signalState = 0;
    private int signalStateCounter = 0;
    private int signalThreshold = 4;
    private int[] last6 = {0, 0, 0, 0, 0, 0};
    private int last6Pos = 0;
    private int prevRA = 0;
    private int curRA = 0;
    private int prevDiff;
    private int curDiff;
    private int startCountdown = 30; // 30 cycles pause before start recognizing commands.
    private int streak = 0;
    private boolean commandDetected = false;

    public ScreamCommandComponent() {
        resetValues();
    }

    private void resetValues() {
        prefix = 10; // мин допустимое время отсутствия сигнала до начала последовательности (не использ.)
        postfix = 10; // мин допустимое время отсутствия сигнала после конца последовательности
        signalState = 0;
        signalStateCounter = 0;
        signalThreshold = 4;
        startCountdown = 30; // 30 cycles pause before start recognizing commands.
        streak = 0;
    }

    public boolean isCommandDetected() {
        return commandDetected;
    }

    public void setCommandDetected(boolean commandDetected) {
        this.commandDetected = commandDetected;
        resetValues();
    }

    /**
     * Метод получает следующее значение громкости, пересчитывает все внутренние данные и пытается задетектить команду
     *
     * @param value
     */
    public void nextValue(int value) {

        if (commandDetected) { // no action needed - clear the command first: setCommandDetected(false)
            return;
        }

        last6[last6Pos] = value;
        last6Pos = (last6Pos + 1) % 6;
        prevRA = curRA;
        curRA = last6Average();
        prevDiff = curDiff;
        curDiff = curRA - prevRA;

        if (prevDiff > 0) {
            if (curDiff > 0) {
                streak++;
            } else {
                streak = -1;
            }
        } else {
            if (curDiff > 0) {
                streak = 1;
            } else {
                streak--;
            }
        }


        if (startCountdown > 0) {  //starting sequence 1 sec not finished - return
            startCountdown--;
            return;
        }

        switch (signalState) {
            case 0:  // pre-sequence state
                if (streak == 1) { //possible start of command detected
                    signalState = 1;
                }
                break;
            case 1:  // 1st up: there must be not less than qqPattern positive signals
                if (streak < 0) { // too early, not qualified for signal
                    signalState = 0;
                    return;
                }
                if (streak == qqPattern[0][0]) { // signal is long enough - go to the next state
                    signalState = 2;
                }
                break;
            case 2:  // still 1st positive: there must be not more than qqPattern 1,2 positive signals
                if (streak < 0) { // good! promoted for 1st down signal
                    signalState = 3;
                    return;
                }
                if (streak > qqPattern[1][0]) { // too long - not qualified for signal
                    signalState = 0;
                }
                break;
            case 3:  // 1st negative after correct 1st positive detected
                if (streak > 0) { // too early, not qualified for signal
                    signalState = 0;
                    return;
                }
                if (streak == qqPattern[0][1]) { // promoted for possible 2-nd positive signal
                    signalState = 4;
                }
                break;
            case 4:  // still 1st negative: there must be not more than qqPattern 2,2 signals
                if (streak > 0) { // promoted for 2nd up signal
                    signalState = 5;
                    return;
                }
                if (streak < qqPattern[1][1]) { // not qualified for signal - too long
                    signalState = 0;
                }
                break;
            case 5:  // 2nd positive signal after correct 1-st + and - detected
                if (streak < 0) { // too early, not qualified
                    signalState = 0;
                    return;
                }
                if (streak == qqPattern[0][2]) { // possible 2nd positive detected
                    signalState = 6;
                }
                break;
            case 6:  // 2-nd positive must be not more than qqPattern 3,2
                if (streak < 0) { // good! promoted for 2nd down signal
                    signalState = 7;
                    return;
                }
                if (streak > qqPattern[1][2]) { // not qualified for signal - too long
                    signalState = 0;
                }
                break;
            case 7:  // 2nd negative after correct 2nd positive detected
                if (streak > 0) { // too early, not qualified for signal
                    signalState = 0;
                    return;
                }
                if (streak == qqPattern[0][3]) { // possibly 2-nd negative signal detected
                    signalState = 8;
                }
                break;
            case 8:  // still 2nd down: there must be not more than qqPattern max signals
                if (streak > 0) { // promoted for the final - postsignal state
                    signalState = 9;
                    return;
                }
                if (streak < qqPattern[1][3]) { // not qualified for signal - too long
                    signalState = 0;
                }
                break;
            case 9:  // signal sequence was ok - just wait for NO MORE other signals next few cycles
                if (streak > signalThreshold) { // not good  -  something else detected. Signal does not qualify for command
                    signalState = 0;
                    return;
                }
                if (signalStateCounter++ > postfix) { // Hurraaaay! long enough no activity detected after the valid signals sequence, flag the command detected!
                    signalState = 0;
                    signalStateCounter = 0;
                    commandDetected = true;
                }
                break;
        }

    }

    private int absolute(int streak) {
        return (streak > 0) ? streak : (0 - streak);
    }

    private int last6Average() {
        int result = 0;
        for (int i = 0; i < last6.length; i++) {
            result += last6[i];
        }
        return result / last6.length;
    }


}
