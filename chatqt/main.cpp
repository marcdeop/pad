#include "mainwindow.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    QTcpSocket * socket = new QTcpSocket();

    MainWindow w(socket);
    w.show();

    return a.exec();
}
