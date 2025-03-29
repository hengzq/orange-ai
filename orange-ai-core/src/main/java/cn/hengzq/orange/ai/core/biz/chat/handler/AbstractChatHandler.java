package cn.hengzq.orange.ai.core.biz.chat.handler;

public abstract class AbstractChatHandler implements ChatHandler {

    protected ChatHandler next;

    protected abstract void before(ChatContext context);

    protected abstract void execute(ChatContext context);

    protected abstract void after(ChatContext context);

    @Override
    public ChatHandler next(ChatHandler next) {
        this.next = next;
        return next;
    }

    @Override
    public void handle(ChatContext context) {
        this.before(context);
        this.execute(context);
        if (this.next != null) {
            this.next.handle(context);
        }
        this.after(context);
    }
}
