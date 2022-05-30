# See DiscordJS repo
[DiscordJS Bot](https://github.com/zacierka/switchbot)

This code is the interface for the application. Messages are recieved
through discord. The messages are parsed through a command handler,
then executed through the appropriate command. The bot uses a few
integrations for data messaging and data storage. Data storage is handled
through Sequelize and data messaging is done with RabbitMQ. 

message flow for online command.

Discord Message from user -> cmd handler -> cmd run method -> 
send ampq message to broker


JAVA side
Consume Message -> cmd handler -> cmd execute() -> discord webhook