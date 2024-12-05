document.addEventListener("DOMContentLoaded", function () {
    const chatHistory = document.getElementById("chatHistory");
    const queryInput = document.getElementById("queryInput");
    const sendButton = document.getElementById("sendButton");
  
    sendButton.addEventListener("click", function () {
      const query = queryInput.value.trim();
      if (query !== "") {
        appendUserMessage(query);
        // Call a function here to handle sending the user query and receiving the bot response
        // For now, let's simulate a bot response
        const botResponse = "I'm a chatbot! How can I help?";
        appendBotMessage(botResponse);
        queryInput.value = ""; // Clear the input
      }
    });
  
    function appendUserMessage(message) {
      const userMessageElement = createMessageElement(message, false);
      chatHistory.appendChild(userMessageElement);
      chatHistory.scrollTop = chatHistory.scrollHeight;
    }
  
    function appendBotMessage(message) {
      const botMessageElement = createMessageElement(message, true);
      chatHistory.appendChild(botMessageElement);
      chatHistory.scrollTop = chatHistory.scrollHeight;
    }
  
    function createMessageElement(message, isBot) {
      const messageElement = document.createElement("div");
      messageElement.className = "message" + (isBot ? " bot-message" : "");
      const messageContent = document.createElement("div");
      messageContent.className = "message-content";
      if (isBot) {
        const messageIcon = document.createElement("span");
        messageIcon.className = "message-icon";
        messageIcon.textContent = "🤖";
        messageContent.appendChild(messageIcon);
      }
      const messageText = document.createElement("p");
      messageText.textContent = message;
      messageContent.appendChild(messageText);
      messageElement.appendChild(messageContent);
      return messageElement;
    }
  });
  