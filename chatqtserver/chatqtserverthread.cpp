#include "chatqtserverthread.h"

#include <QtNetwork>

ChatQtServerThread::ChatQtServerThread(int socketDescriptor, const QString &fortune, QObject *parent)
    : QThread(parent), socketDescriptor(socketDescriptor), text(fortune)
{
}

void ChatQtServerThread::run()
{
    QTcpSocket tcpSocket;
    if (!tcpSocket.setSocketDescriptor(socketDescriptor)) {
        emit error(tcpSocket.error());
        return;
    }

    QByteArray block;
    QDataStream out(&block, QIODevice::WriteOnly);
    out.setVersion(QDataStream::Qt_4_0);
    out << text;

    tcpSocket.write(block);
    tcpSocket.disconnectFromHost();
    tcpSocket.waitForDisconnected();
}

