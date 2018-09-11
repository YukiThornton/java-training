package clock;

enum TimerType {
    WORK_DEFAULT(ColorPalette.BLUE, Role.WORK),
    BREAK_DEFAULT(ColorPalette.YELLOW, Role.BREAK);

    private ColorPalette palette;
    private Role role;

    private TimerType(ColorPalette palette, Role role) {
        this.palette = palette;
        this.role = role;
    }

    ColorPalette colorPalette() {
        return palette;
    }

    boolean isWorkTimer() {
        return role == Role.WORK;
    }

    boolean isBreakTimer() {
        return role == Role.BREAK;
    }

    String initialTimerName() {
        return role.initialTimerName;
    }

    String verbPhrase() {
        return role.verbPhrase;
    }

    int initialTimerSeconds() {
        return role.initialTimerSeconds;
    }

    private enum Role {
        WORK("Work", "work", 1500), BREAK("Break", "take a break", 300);

        private String initialTimerName;
        private String verbPhrase;
        private int initialTimerSeconds;

        private Role(String initialTimerName, String verbPhrase, int initialTimerSeconds) {
            this.initialTimerName = initialTimerName;
            this.verbPhrase = verbPhrase;
            this.initialTimerSeconds = initialTimerSeconds;
        }
    }

}
