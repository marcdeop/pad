#include "mainwindow.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    QTcpSocket * socket = new QTcpSocket();

    // Connect to the server
    socket->connectToHost(argv[1], QByteArray(argv[2]).toInt());

    // Wait until we stablish a connection
    if(!socket->waitForConnected(5000))
    {
        qDebug() << "Error: " << socket->errorString();
    }

    MainWindow w(socket);
    w.show();

    return a.exec();
}
