#include <QApplication>
#include <QtCore>
#include <chatqtserver.h>

#include <stdlib.h>
#include <QDebug>

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    ChatQtServer *chatQtServer = new ChatQtServer();
    chatQtServer->listen();

    qDebug() << "ALOHA";
    qDebug() << chatQtServer->serverPort();
    return app.exec();
}

