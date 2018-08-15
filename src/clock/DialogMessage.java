package clock;

enum DialogMessage {
    REPORT("Good job!"),
    INVALID_TIMER_NAME("You can't!", "Too long!"),
    INVALID_TIMER_DURATION("You can't!", "Set time between 1 and 999.");

    private ContentPolicy contentPolicy;
    private String title;
    private String content;

    private enum ContentPolicy {FIXED, DYNAMIC};

    private DialogMessage(String title, String content) {
        this.contentPolicy = ContentPolicy.FIXED;
        this.title = title;
        this.content = content;
    }

    private DialogMessage(String title) {
        this.contentPolicy = ContentPolicy.DYNAMIC;
        this.title = title;
    }

    DialogMessage setContent(String content) {
        if (this.contentPolicy == ContentPolicy.FIXED) {
            throw new IllegalStateException("Content already exists.");
        }
        this.content = content;
        return this;
    }

    String title() {
        return this.title;
    }

    String content() {
        return this.content;
    }
}
