# Pre-work - *To do*

**To do** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Sandeep Raveesh**

Time spent: **30** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Search feature on the menubar to search for the todo items
* [x] Add or edit todo items via voice
* [x] Resizable widget displays the todo items and the due date in the home screen
* [x] Integration with sharing on Android. Share the todo items via whatsapp, facebook, twitter, gmail, hangouts, slack, facebook, messenger and many other apps
* [x] Quick task bar to add some todo item quickly
* [x] Confirm delete using a dialog
* [x] Improved UI/UX

## Video Walkthrough 

Here's a walkthrough of implemented user stories (I will soon upload a video with add/edit todo tasks via voice from my phone, I was unable to get Genymotion working with audio input):

<img src='todoApp.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

* Adding search functionality for listView with Custom Adapter was harder
* Problem with audio input in Genymotion
* Setting up ActiveAndroid took a while

## License

    Copyright [2016] [Sandeep Raveesh]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
