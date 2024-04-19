import { Component } from '@angular/core';
import { dialogflowService } from '../services/dialogflowService';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {databaseService} from '../services/databaseService';
import { OnInit } from '@angular/core';
import { DurationPipe } from '../api/flight-offers/duration.pipe';

@Component({
  standalone: true,
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.css'],
  imports: [CommonModule,DurationPipe,FormsModule,]
})
export class ConversationComponent implements OnInit {
  responseMessage: string = '';
  userMessage: any;
  dialogues: Array<{ sender: 'user' | 'agent', message: string | object }> = [];

  constructor(private dialogflowService: dialogflowService, private databaseService: databaseService) { }

  ngOnInit() {
    console.log("ngOnInit is used")
    this.initDialogflowAgent();
    this.loadSessionMessages('1002')
  }

  loadSessionMessages(sessionId: string) {
    this.databaseService.getSessionMessages(sessionId).subscribe({
      next: (dialogues) => {
        this.dialogues = dialogues.map(dialogue => ({
          sender: dialogue.sender,
          message: dialogue.message
        }));
        console.log('Dialogues loaded:', dialogues);
      },
      error: (error) => console.error('Error loading dialogues:', error)
    });
  }

  initDialogflowAgent() {
    this.dialogflowService.fetchInitResponse().subscribe({
      next: (response: any) => {
        // this.messages.push(`Bot: ${response.message}`);
        console.log("response:",response)
        this.responseMessage = response.message;
      },
      error: (error) => console.error('Error initializing the conversation:', error)
    });
  }

  sendMessage() {
    
    console.log("sendMessage is activated,store information in database")
    // TODO: change the ID
    const id = "1002";
    if (!this.userMessage.trim()) return; // message cannot be empty
    const currentTimestamp = new Date().toISOString();
    this.databaseService.saveMessage(id, this.userMessage,currentTimestamp,'user').subscribe({
      next: (response) => console.log('User message saved', response),
      error: (error) => console.error('Error saving user message:', error)
    });

    this.dialogues.push({ sender: 'user', message: this.userMessage });

    this.dialogflowService.fetchResponse(this.userMessage).subscribe({
      next: (response: any) => {
        console.log("response:", response);
        this.responseMessage = response.message;
        const currentTimestampAgent = new Date().toISOString();
        this.dialogues.push({ sender: 'agent', message: response.message });

        this.databaseService.saveMessage(id, response.message,currentTimestamp,'agent').subscribe({
          next: (response) => console.log('agent message saved', response),
          error: (error) => console.error('Error saving agent message:', error)
        });
      },
      error: (error) => console.error('Error sending the message:', error)
    });

    this.userMessage = '';
  }

  adjustTextareaHeight(textarea: HTMLTextAreaElement) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
  }
}