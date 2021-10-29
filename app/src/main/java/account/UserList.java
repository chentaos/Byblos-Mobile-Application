package account;

import java.util.ArrayList;

public interface UserList<E extends User> {
    int pageLength = 10;
    void getNextpage(ListenerCallBack callBack);
    void getPrevPage(ListenerCallBack callBack);
    ArrayList<E> getList();
    void delete(String userName);
}
