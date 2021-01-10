package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.thiagoperea.deliverymuchtest.R
import com.thiagoperea.deliverymuchtest.data.model.Repository
import org.koin.android.viewmodel.ext.android.viewModel

class RepositoryListActivity : AppCompatActivity() {

    private val viewModel by viewModel<RepositoryListViewModel>()

    private var adapter: RepositoryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupToolbar()
        setupSearchBar()
        setupList()
        setupObservers()
    }

    private fun setupList() {
        this.adapter = RepositoryListAdapter(::onRepositoryClick)

        findViewById<RecyclerView>(R.id.repositoryListView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RepositoryListActivity)
            adapter = this@RepositoryListActivity.adapter
            addItemDecoration(DividerItemDecoration(this@RepositoryListActivity, VERTICAL))
        }
    }

    private fun onRepositoryClick(repository: Repository) {
        MaterialAlertDialogBuilder(this)
            .setView(RepositoryDetailsView(layoutInflater, repository).createView())
            .setNeutralButton("Fechar", null)
            .show()
    }

    private fun setupObservers() {
        viewModel.searchState.observe(this) {
            when (it) {
                is RepositoryListState.Loading -> onSearchLoading()
                is RepositoryListState.Success -> onSearchSuccess(it.repositories)
                is RepositoryListState.Error -> onSearchError(it.errorMessage)
            }
        }

        viewModel.isDayThemeState.observe(this) { isDayTheme ->
            if (isDayTheme) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun onSearchError(errorMessage: String?) {
        hideLoading()
        findViewById<ImageView>(R.id.repositoryListPlaceholder).visibility = View.VISIBLE
        Toast.makeText(this, "ERRO: $errorMessage", Toast.LENGTH_SHORT).show()
        viewModel.clearErrorEmitter()
    }

    private fun onSearchSuccess(repositories: List<Repository>?) {
        hideLoading()
        adapter?.setData(repositories)
        if (repositories?.isEmpty() == true) {
            findViewById<ImageView>(R.id.repositoryListPlaceholder).visibility = View.VISIBLE
        }
    }

    private fun onSearchLoading() {
        adapter?.clearData()
        showLoading()
    }

    private fun showLoading() {
        findViewById<ProgressBar>(R.id.repositoryListLoading).visibility = View.VISIBLE
        findViewById<RecyclerView>(R.id.repositoryListView).visibility = View.GONE
        findViewById<ImageView>(R.id.repositoryListPlaceholder).visibility = View.GONE
    }

    private fun hideLoading() {
        findViewById<ProgressBar>(R.id.repositoryListLoading).visibility = View.GONE
        findViewById<RecyclerView>(R.id.repositoryListView).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.repositoryListPlaceholder).visibility = View.GONE
    }

    private fun setupSearchBar() {
        findViewById<TextInputLayout>(R.id.repositoryListSearchField).apply {
            setEndIconOnClickListener {
                viewModel.doSearch(this.editText?.text.toString())
            }

            editText?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.doSearch(this.editText?.text.toString())
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }

    }

    private fun setupToolbar() {
        findViewById<MaterialToolbar>(R.id.repositoryListToolbar)?.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.dayNightMenu) {
                viewModel.switchDayNight()
            }
            return@setOnMenuItemClickListener true
        }
    }
}

class RepositoryDetailsView(
    private val inflater: LayoutInflater,
    private val repository: Repository
) {

    fun createView(): View = inflater.inflate(R.layout.dialog_repository_details, null).apply {

        Glide.with(inflater.context)
            .load(repository.author.avatarUrl)
            .placeholder(R.drawable.ic_github)
            .error(R.drawable.ic_launcher_foreground)
            .fitCenter()
            .into(this.findViewById(R.id.dialogRepositoryImage))

        this.findViewById<TextView>(R.id.dialogRepositoryTitle).text = repository.name
        this.findViewById<TextView>(R.id.dialogRepositoryAuthor).text = repository.author.username
        this.findViewById<TextView>(R.id.dialogRepositoryDescription).text = repository.description
    }
}
