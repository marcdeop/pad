#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QTcpSocket>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public slots:
    void sendMessage();
    void receiveText();
    void onAddNick(QByteArray nick);
    void onRemoveNick(QByteArray nick);
    void onConnected();
    void onDisconnected();
    void onConnectTo(QString host, int port);
    void onDisconnect();
    void toggleConnection();
    void changeNick();

signals:
    void addNick(QByteArray nick);
    void removeNick(QByteArray nick);
    void connectTo(QString host, int port);
    void disconnect();

public:
    explicit MainWindow(QTcpSocket * socket, QWidget *parent = 0);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    QTcpSocket * socket;
    void addActions();
    void addMenus();
    void connectSlots();
    QAction * exitAction;
    QAction * connectionAction;
    QAction * changeNickAction;
    QMenu * fileMenu;
};

#endif // MAINWINDOW_H
