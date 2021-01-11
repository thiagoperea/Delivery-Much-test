# Delivery-Much-test

App criado para avaliação das técnicas de desenvolvimento Android.

## Arquitetura utilizada

![arquitetura](readme-files/arquitetura.png =200x)

## Implementing the app

Although the parent sample already used ViewModels, as it was following an MVVM architecture, the Architecture Components have different restrictions by design.

In the MVVM architecture, Views react to changes in the ViewModel without being explicitly called. However, the MVVM architecture presents some challenges when working with some Android components. 

### Live events

A new `SingleLiveEvent` class is created, which extends `MutableLiveData` so it's lifecycle-aware. It's used for communication between ViewModels and UI views (activities and fragments).

Instead of holding data, it dispatches data once. This is important to prevent events being fired after a rotation, for example.

A convenient use for this is navigation. There is no reference to the View from a ViewModel so the communication between them must happen via a subscription. ViewModels expose
events like `openTaskEvent` and views subscribe to them. For example:

```java
private void subscribeToNavigationChanges(TaskDetailViewModel viewModel) {
    // The activity observes the navigation commands in the ViewModel
    viewModel.getEditTaskCommand().observe(this, new Observer<Void>() {
        @Override
        public void onChanged(@Nullable Void _) {
            TaskDetailActivity.this.onStartEditTask();
        }
    });
    viewModel.getDeleteTaskCommand().observe(this, new Observer<Void>() {
        @Override
        public void onChanged(@Nullable Void _) {
            TaskDetailActivity.this.onTaskDeleted();
        }
    });
}
```

### Snackbar

To show a [`Snackbar`](https://developer.android.com/reference/android/support/design/widget/Snackbar.html), you must use a static call to pass a view object:

```java
Snackbar.make(View coordinatorLayout, String text, int length).show();
```

A ViewModel, however, doesn't have the necessary reference to the view hierarchy. Instead, you can manually subscribe the snackbar to a Snackbar event. In this case the subscription
is made to a `SnackbarMessage` which extends a `SingleLiveEvent` and takes a string resource ID (an integer). There's only one snackbar and there should only be one active observer
at a time. Messages are only shown once.

### TasksAdapter
There is no `TaskItemViewModel` in this branch for each particular item in the list, so tasks in the list only communicate with the list's ViewModel.

### Using ViewModels in bindings with the Data Binding Library
ViewModels are used to show data of a particular screen, but they don't handle user actions. For that it's much more convenient to create user actions listeners or even presenters
that hold no state during configuration changes and hence are easy to recreate. See `TaskItemUserActionsListener` for an example.

### Repository does not use LiveData
For simplicity and similarity with the parent branch, the repository does not use LiveData to expose its data.