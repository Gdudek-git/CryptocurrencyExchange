package controller.operation_on_wallet;

import javafx.scene.control.Label;
import model.database.DatabaseConnectionModel;
import model.session.ChangeUserDataModel;
import org.hibernate.Session;

public abstract class AbstractOperationOnWalletViewController {

    private Session session;
    private DatabaseConnectionModel databaseConnectionModel;
    private ChangeUserDataModel changeUserDataModel;


    protected AbstractOperationOnWalletViewController()
    {
        databaseConnectionModel = new DatabaseConnectionModel();
        changeUserDataModel = new ChangeUserDataModel();
    }

    protected void showInfo(Label label, String info)
    {
        label.setText(info);
    }

    protected void resetLabel(Label label)
    {
        label.setText("");
    }

    protected void establishConnectionWithDatabase() {
        Thread thread = new Thread(() -> session =  databaseConnectionModel.getSessionObj());
        thread.start();
    }

    protected void closeConnection()
    {
        databaseConnectionModel.closeConnection(session);
    }

    protected void updateLoggedUserData()
    {
        changeUserDataModel.updateLoggedUserData(session);
    }

    protected Session getSession()
    {
        return session;
    }

    protected ChangeUserDataModel getChangeUserDataModel()
    {
        return changeUserDataModel;
    }

}
