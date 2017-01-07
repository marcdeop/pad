#include "chatqtserver.h"
#include "chatqtserverthread.h"

#include <stdlib.h>

ChatQtServer::ChatQtServer(QObject *parent)
    : QTcpServer(parent)
{
}

void ChatQtServer::incomingConnection(qintptr socketDescriptor)
{
    ChatQtServerThread *thread = new ChatQtServerThread(socketDescriptor, "write this to client\n", this);
    connect(thread, SIGNAL(finished()), thread, SLOT(deleteLater()));
    thread->start();
}

