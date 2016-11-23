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

signals:
    void addNick(QByteArray nick);
    void removeNick(QByteArray nick);

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    QTcpSocket * socket;
};

#endif // MAINWINDOW_H
